package com.amar.blog.repository;

import com.amar.blog.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Subscription findByEmail(String name);
}
