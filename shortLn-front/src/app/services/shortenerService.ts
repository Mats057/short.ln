import { inject, Injectable } from '@angular/core';
import { UrlStats } from '../models/urlStats.model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Url } from '../models/url.model';
import { environment } from '../../enviroments/enviroment.dev';

@Injectable({
  providedIn: 'root',
})
export class ShortenerService {
  private http = inject(HttpClient);

  public getShortenedUrl(shortCode: string): Observable<Url> {
    return this.http.get<Url>(`${environment.API_HOST}/shorten/${shortCode}`);
  }

  public getShortenedUrlStatistics(): Observable<UrlStats> {
    return this.http.get<UrlStats>(`${environment.API_HOST}/shorten/stats`);
  }

  public createShortenedUrl(url: string): Observable<Url> {
    return this.http.post<Url>(`${environment.API_HOST}/shorten`, { url });
  }

  public updateShortenedUrl(url: string, shortCode: string): Observable<any> {
    return this.http.put<Url>(`${environment.API_HOST}/shorten/${shortCode}`, {
      url,
    });
  }

  public deleteShortenedUrl(shortCode: string): Observable<any> {
    return this.http.delete(`${environment.API_HOST}/shorten/${shortCode}`);
  }
}
