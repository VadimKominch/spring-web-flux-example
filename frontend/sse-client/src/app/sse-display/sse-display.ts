import { Component, OnDestroy, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';  
import { SseService } from '../sse';
import { Chart, ChartConfiguration, registerables } from 'chart.js';
import { Subscription } from 'rxjs';

export interface DataPoint {
  x: number;
}

Chart.register(...registerables); 

@Component({
  selector: 'app-sse-display',
  styleUrl: './sse-display.css',
  imports: [CommonModule], 
  standalone: true,
  template: `
    <h2>Wweb flux noise data plot</h2>
    <canvas id="sseChart"></canvas>
  `
})
export class SseDisplayComponent implements OnInit, OnDestroy {
  private chart?: Chart;
  private sseSubscription?: Subscription;
  private maxDataPoints = 200; // limit points on chart
  private threshold = 70;

  constructor(private sseService: SseService) {}

  ngOnInit(): void {
        const ctx = (document.getElementById('sseChart') as HTMLCanvasElement).getContext('2d');
        if (!ctx) return;

        this.chart = new Chart(ctx, {
          type: 'line',
          data: {
            labels: [],
            datasets: [{
              label: 'Random noise',
              data: [],
              borderColor: 'blue',
              pointBackgroundColor: (ctx) => {
                const value = ctx.raw as number;
                return value > this.threshold ? 'red' : 'blue';
              },
              pointRadius: 5,
              fill: true,
              backgroundColor: (ctx) => {
                const value = ctx.raw as number;
                return value > this.threshold ? 'rgba(255,0,0,0.2)' : 'rgba(0,0,255,0.2)';
              },
              segment: {
                borderColor: ctx => {
                  const v = ctx.p1.parsed.y;
                  return v > this.threshold ? 'red' : 'blue';
                },
                backgroundColor: ctx => {
                  const v = ctx.p1.parsed.y;
                  return v > this.threshold ? 'rgba(255,0,0,0.2)' : 'rgba(0,0,255,0.2)';
                }
              }
            }]
          },
          options: {
            animation: false,
            responsive: true,
            scales: {
              x: { title: { display: true, text: 'Time' },       ticks: {maxRotation: 90, minRotation: 90, align: 'center'} },
              y: { title: { display: true, text: 'Value' } }
            }
          }
        });

        this.sseSubscription = this.sseService
          .getServerSentEvent('http://localhost:8080/data', 'number-event')
          .subscribe({
            next: (msg) => {
              const obj: DataPoint = JSON.parse(msg);
              this.addData(obj.x);
        },
            error: (err) => console.error('SSE error:', err)
          });
  }

  private addData(value: number) {
    if (!this.chart) return;

    const data = this.chart.data.datasets[0].data as number[];
    const labels = this.chart.data.labels as string[];

    const now = new Date();
    labels.push(this.formatTime(now));
    data.push(value);

    // Keep only the last maxDataPoints
    if (data.length > this.maxDataPoints) {
      data.shift();
      labels.shift();
    }

    this.chart.update('none'); // update chart without animation
  }

  private formatTime(date: Date) {
    const hours = ("0" + date.getHours()).slice(-2);
    const minutes = ("0" + date.getMinutes()).slice(-2);
    const seconds = ("0" + date.getSeconds()).slice(-2);
    return `${hours}:${minutes}:${seconds}`;
  }

  ngOnDestroy(): void {
    this.sseSubscription?.unsubscribe();
  }
}
