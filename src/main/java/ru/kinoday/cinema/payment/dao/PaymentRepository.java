package ru.kinoday.cinema.payment.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kinoday.cinema.payment.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
