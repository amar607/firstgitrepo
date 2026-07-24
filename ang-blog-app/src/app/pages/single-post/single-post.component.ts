import { AfterViewChecked, Component, ElementRef, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PostServiceService } from 'src/app/post-service.service';

import hljs from 'highlight.js/lib/core';
// import languages you expect, example:
import java from 'highlight.js/lib/languages/java';
import sql from 'highlight.js/lib/languages/sql';
import javascript from 'highlight.js/lib/languages/javascript';

hljs.registerLanguage('java', java);
hljs.registerLanguage('sql', sql);
hljs.registerLanguage('javascript', javascript);

@Component({
  selector: 'app-single-post',
  templateUrl: './single-post.component.html',
  styleUrls: ['./single-post.component.css']
})
export class SinglePostComponent  implements OnInit, AfterViewChecked {
  post: any;
  private contentRendered = false;

 constructor(
    private route: ActivatedRoute,
    private postService: PostServiceService,
    private el: ElementRef
  ) {}

  ngOnInit(): void {
    // const postId = this.route.snapshot.paramMap.get('id');

    this.route.paramMap.subscribe(params => {
        const postId = params.get('id');
        if (postId) {
          this.postService.getPostById(postId).subscribe((data) => {
            this.post = data;
            this.contentRendered = false; // reset flag so AfterViewChecked will highlight again
          });
        }
    });
  }

  ngAfterViewChecked(): void {
     if (this.post && !this.contentRendered) {
      this.highlightAllCode();
      this.contentRendered = true;
    }
  }

  private highlightAllCode() {
    // Find all <pre><code> or any code blocks inside your content container
    const nativeElement = this.el.nativeElement;
    const codeBlocks = nativeElement.querySelectorAll('pre code, .ql-code-block');

    codeBlocks.forEach((block: HTMLElement) => {
      hljs.highlightElement(block);
    });
  }

}
