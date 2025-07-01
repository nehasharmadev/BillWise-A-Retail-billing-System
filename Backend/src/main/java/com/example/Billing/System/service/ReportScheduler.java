package com.example.Billing.System.service;

import com.example.Billing.System.Util.MailUtility;
import com.example.Billing.System.entity.Subscriber;
import com.example.Billing.System.io.OrderResponse;

//public class ReportScheduler {
//
//}

//package com.example.Billing.System.service;

//import com.example.Billing.System.model.Order;
//import com.example.Billing.System.model.Subscriber;

import com.example.Billing.System.repository.SubscriberRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportScheduler {

    private final SubscriberRepository subscriberRepository;
    private final OrderService orderService;
    private final EmailService emailService;
    private final MailUtility mailUtil; 

    @Scheduled(cron = "0 0 9 * * *") // Every day at 9 AM
//    @Scheduled(cron = "*/30 * * * * *")
    @Transactional
    public void sendDailyReports() throws Exception {
        List<Subscriber> subscribers = subscriberRepository.findAll();
        List<OrderResponse> orders = orderService.getTodayOrders(); 
        File report = mailUtil.generateSalesReport(orders);

        for (Subscriber sub : subscribers) {
            try {
                emailService.sendEmailWithAttachment(
                    sub.getEmail(),
                    "Daily Sales Report",
                    "Please find yesterday's sales report attached.",
                    report
                );
            } catch (MessagingException e) {
                System.err.println("Failed to send to " + sub.getEmail() + ": " + e.getMessage());
            }
        }
    }

}
