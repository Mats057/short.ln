import {
  Component,
  ElementRef,
  inject,
  signal,
  ViewChild,
} from '@angular/core';
import { ShortenerService } from '../../services/shortenerService';
import { UrlStats } from '../../models/urlStats.model';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-statistics',
  imports: [DatePipe],
  templateUrl: './statistics.html',
  styleUrl: './statistics.scss',
})
export class Statistics {
  private shortenerService = inject(ShortenerService);
  statistics = signal<UrlStats | undefined>(undefined);

  @ViewChild('shortenedurl') shortenedUrl!: ElementRef;
  @ViewChild('statisticserror') statisticsError!: ElementRef;

  handleClick() {
    const response = this.shortenerService.getShortenedUrlStatistics(
      this.shortenedUrl.nativeElement.value
    );
    response.subscribe({
      next: (r) => {
        this.statistics.set(r);
      },
      error: (e) => {
        this.statistics.set(undefined);
        this.statisticsError.nativeElement.style.color = '#FF0000';
        this.statisticsError.nativeElement.innerText =
          e.error.error || 'Unexpected Error. Try Again Later';
      },
    });
  }
}
