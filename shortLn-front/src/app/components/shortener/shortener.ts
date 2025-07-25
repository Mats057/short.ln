import {
  Component,
  ElementRef,
  inject,
  signal,
  ViewChild,
} from '@angular/core';
import { ShortenerService } from '../../services/shortenerService';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-shortener',
  imports: [CommonModule],
  templateUrl: './shortener.html',
  styleUrl: './shortener.scss',
})
export class Shortener {
  private shortenerService = inject(ShortenerService);
  shortenedUrl = signal('');
  errorMessage = signal('');

  @ViewChild('originalurl') originalUrl!: ElementRef;

  handleClick() {
    const response = this.shortenerService.createShortenedUrl(
      this.originalUrl.nativeElement.value
    );
    response.subscribe({
      next: (r) => {
        this.shortenedUrl.set(`http://localhost:4200/rdr/${r.shortCode}`);
        this.errorMessage.set('');
      },
      error: (e) => {
        this.shortenedUrl.set('');
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
