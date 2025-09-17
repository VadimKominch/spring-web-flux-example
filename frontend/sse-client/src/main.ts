import 'zone.js';

import { bootstrapApplication } from '@angular/platform-browser';
import { SseDisplayComponent } from './app/sse-display/sse-display';
import { importProvidersFrom } from '@angular/core';
import { CommonModule } from '@angular/common';
import { provideHttpClient } from '@angular/common/http';
import { SseService } from './app/sse';

bootstrapApplication(SseDisplayComponent, {
  providers: [
    provideHttpClient(), // Provide HttpClient for your service
    SseService, // Provide your SseService
  ],
}).catch((err) => console.error(err));
