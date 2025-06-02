package by.n1jel.auction.service;

import by.n1jel.auction.dto.ReportRequestDto;
import by.n1jel.auction.dto.ReportResponseDto;

public interface ReportService {
    ReportResponseDto getReport(ReportRequestDto reportRequestDto);
}
