package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.entities.OtpToken;
import com.swp.ChildrenVaccine.repository.OtpTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {
    @Autowired
    private OtpTokenRepository otpTokenRepository;

    public String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000); // 6 số
    }

    @Transactional
    public void saveOtp(String email, String otp) {
        // Tìm OTP theo email
        Optional<OtpToken> existingToken = otpTokenRepository.findByEmail(email);

        if (existingToken.isPresent()) {
            // Nếu email đã tồn tại, cập nhật lại OTP và thời gian hết hạn
            OtpToken otpToken = existingToken.get();
            otpToken.setOtp(otp);
            otpToken.setExpiryTime(LocalDateTime.now().plusMinutes(5));
            otpTokenRepository.save(otpToken);
        } else {
            // Nếu email chưa tồn tại, tạo otp mới
            OtpToken newOtpToken = new OtpToken();
            newOtpToken.setEmail(email);
            newOtpToken.setOtp(otp);
            newOtpToken.setExpiryTime(LocalDateTime.now().plusMinutes(5));
            otpTokenRepository.save(newOtpToken);
        }
    }

    public boolean validateOtp(String email, String otp) {
        Optional<OtpToken> otpToken = otpTokenRepository.findByEmail(email);
        return otpToken.isPresent() &&
                otpToken.get().getOtp().equals(otp) &&
                otpToken.get().getExpiryTime().isAfter(LocalDateTime.now());
    }
}
