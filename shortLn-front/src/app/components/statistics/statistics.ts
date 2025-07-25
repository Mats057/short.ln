import {
  Component,
  ElementRef,
  inject,
  signal,
  ViewChild,
} from '@angular/core';
import { ShortenerService } from '../../services/shortenerService';
import { UrlStats } from '../../models/urlStats.model';
import { CommonModule, DatePipe } from '@angular/common';

@Component({
  selector: 'app-statistics',
  imports: [DatePipe, CommonModule],
  templateUrl: './statistics.html',
  styleUrl: './statistics.scss',
})
export class Statistics {
  private shortenerService = inject(ShortenerService);
  statistics = signal<UrlStats | undefined>(undefined);
  errorMessage = signal('');

  @ViewChild('shortenedurl') shortenedUrl!: ElementRef;
  @ViewChild('statisticserror') statisticsError!: ElementRef;

  handleClick() {
    const response = this.shortenerService.getShortenedUrlStatistics(
      this.shortenedUrl.nativeElement.value
    );
    response.subscribe({
      next: (r) => {
        this.statistics.set(r);
        this.errorMessage.set('');
      },
      error: (e) => {
        this.statistics.set(undefined);
        this.errorMessage.set(
          e.error.error || 'Unexpected Error. Try Again Later'
        );
        setTimeout(() => {
          this.errorMessage.set('');
        }, 3000);
      },
    });
  }
}
