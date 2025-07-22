import { ShortenerService } from './../../services/shortenerService';
import { Component, ElementRef, inject, ViewChild } from '@angular/core';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.html',
  styleUrls: ['./home.scss']
})
export class Home {

  private shortenerService = inject(ShortenerService)

  @ViewChild('shortenedurl') shortenedUrl!: ElementRef;
  @ViewChild('originalurl') originalUrl!: ElementRef;
  

  handleClick(){
    const response = this.shortenerService.createShortenedUrl(this.originalUrl.nativeElement.value);
    response.subscribe({
      next: (r) => {
        this.shortenedUrl.nativeElement.href = `http://localhost:4200/rdr/${r.shortCode}`
        this.shortenedUrl.nativeElement.innerText = `http://localhost:4200/rdr/${r.shortCode}`
      },
      error: (e) => {
          this.shortenedUrl.nativeElement.style.color = "#FF0000"
          this.shortenedUrl.nativeElement.innerText = e.error.error
        }
    })

  }
}
