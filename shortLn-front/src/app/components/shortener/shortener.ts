import { Component, ElementRef, inject, ViewChild } from '@angular/core';
import { ShortenerService } from '../../services/shortenerService';

@Component({
  selector: 'app-shortener',
  imports: [],
  templateUrl: './shortener.html',
  styleUrl: './shortener.scss',
})
export class Shortener {
  private shortenerService = inject(ShortenerService);

  @ViewChild('shortenedurl') shortenedUrl!: ElementRef;
  @ViewChild('originalurl') originalUrl!: ElementRef;

  handleClick() {
    const response = this.shortenerService.createShortenedUrl(
      this.originalUrl.nativeElement.value
    );
    response.subscribe({
      next: (r) => {
        this.shortenedUrl.nativeElement.style.color = '#2d5ef1ff';
        this.shortenedUrl.nativeElement.href = `http://localhost:4200/rdr/${r.shortCode}`;
        this.shortenedUrl.nativeElement.innerText = `http://localhost:4200/rdr/${r.shortCode}`;
      },
      error: (e) => {
        this.shortenedUrl.nativeElement.style.color = '#FF0000';
        this.shortenedUrl.nativeElement.innerText = e.error.error || "Unexpected Error. Try Again Later";
      },
    });
  }
}
