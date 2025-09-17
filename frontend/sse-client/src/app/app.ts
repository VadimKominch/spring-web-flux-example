import { Component, signal } from '@angular/core';
import { SseDisplayComponent } from './sse-display/sse-display';

@Component({
  selector: 'app-root',
  imports: [SseDisplayComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('sse-client');
}
