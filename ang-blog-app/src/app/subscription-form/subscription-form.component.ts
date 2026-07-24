import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { SubscriptionService } from '../services/subscription.service';

@Component({
  selector: 'app-subscription-form',
  templateUrl: './subscription-form.component.html',
  styleUrls: ['./subscription-form.component.css']
})
export class SubscriptionFormComponent {
  name = '';
  email = '';
  message = '';
  isSuccess = false;
  isSubmitting = false;

  constructor(private httpSubscriptionService: SubscriptionService) {}

  onSubmit() {
    if (!this.name.trim() || !this.email.trim()) {
      this.message = 'Please enter your name and email.';
      this.isSuccess = false;
      return;
    }

    this.isSubmitting = true;
    this.message = '';

    const payload = {
      name: this.name,
      email: this.email
    };

    this.httpSubscriptionService.subscribe(payload)
      .subscribe({
        next: (result : any) => {
          this.message = result.message;;
          this.isSuccess = true;
          this.isSubmitting = false;

          // clear form
          this.name = '';
          this.email = '';
        },
        error: () => {
          this.message = 'Something went wrong. Please try again later.';
          this.isSuccess = false;
          this.isSubmitting = false;
        }
      });
  }
}