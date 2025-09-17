import { Injectable, NgZone } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SseService {
  private eventSource?: EventSource;
  
  getServerSentEvent(url: string, eventType: string = 'message'): Observable<string> {
    return new Observable<string>((subscriber) => {
      this.eventSource = new EventSource(url);

      const listener = (event: MessageEvent) => subscriber.next(event.data);

      this.eventSource.addEventListener(eventType, listener);

      this.eventSource.onerror = (err) => {
        subscriber.error(err);
        this.eventSource?.close();
      };

      // Cleanup on unsubscribe
      return () => {
        this.eventSource?.removeEventListener(eventType, listener);
        this.eventSource?.close();
      };
    });
  }
}
