import { CanActivateFn, Router } from '@angular/router';
import { TokenStorageService } from './token-storage.service';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = (route, state) => {
const tokenStorageService = inject(TokenStorageService);
  const router = inject(Router);

  const token = tokenStorageService.getToken();

  if (token && token !== '{}') {
    return true; // ✅ token exists
  }

  // ❌ No token → redirect to login
  router.navigate(['/login']);
  return false;
};
