import { Component, ElementRef, signal, ViewChild } from '@angular/core';
import { Shortener } from '../../components/shortener/shortener';
import { Statistics } from '../../components/statistics/statistics';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [Shortener, Statistics, FormsModule],
  templateUrl: './home.html',
  styleUrls: ['./home.scss'],
})
export class Home {
  @ViewChild('selecttype') selectType!: ElementRef;

  componentType = signal('shortener');
}
