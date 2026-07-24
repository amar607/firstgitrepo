import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommentService } from 'src/app/services/comment.service';

@Component({
  selector: 'app-comment-form',
  templateUrl: './comment-form.component.html',
  styleUrls: ['./comment-form.component.css']
})
export class CommentFormComponent {
commentForm: FormGroup;

  constructor(private fb: FormBuilder,
              private commentService: CommentService
  ) {
    this.commentForm = this.fb.group({
      name: ['', Validators.required],
      comment: ['', [Validators.required, Validators.minLength(5)]]
    });
  }

  onSubmit() {
    if (this.commentForm.valid) {
      console.log('Form Data:', this.commentForm.value);
      
      // Here you can call your API
      this.commentService.saveCommentByPostId('', this.commentForm.value);
      this.commentForm.reset();
    } else {
      console.log('Form is invalid');
    }
  }
}
