import { Component, ElementRef, ViewChild } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  featuredPosts = [ /* your featured posts */ ];

  @ViewChild('featuredScroller', { static: false }) featuredScroller!: ElementRef;

  scrollLeft(section: 'featured') {
    if (section === 'featured') {
      this.featuredScroller.nativeElement.scrollBy({
        left: -300,
        behavior: 'smooth'
      });
    }
  }

  scrollRight(section: 'featured') {
    if (section === 'featured') {
      this.featuredScroller.nativeElement.scrollBy({
        left: 300,
        behavior: 'smooth'
      });
    }
  }
}
