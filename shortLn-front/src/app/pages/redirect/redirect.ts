import { Component, ElementRef, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { inject } from '@angular/core';
import { ShortenerService } from '../../services/shortenerService';

@Component({
  selector: 'app-redirect',
  imports: [],
  templateUrl: './redirect.html',
  styleUrl: './redirect.scss'
})
export class Redirect {
  @ViewChild('message') shortenedUrl!: ElementRef;
  private route = inject(ActivatedRoute);
  private shortenerService = inject(ShortenerService);

  ngOnInit() {
    const shortCode = this.route.snapshot.paramMap.get('shortCode');
    if (shortCode) {
      this.shortenerService.getShortenedUrl(shortCode).subscribe({
        next: (urlObj) => {
          window.location.href = urlObj.url;
        },
        error: (e) => {
          this.shortenedUrl.nativeElement.style.color = "#FF0000"
          this.shortenedUrl.nativeElement.innerText = `${e.error.error}. Try again later!`
        }
      });
    }
  }
}