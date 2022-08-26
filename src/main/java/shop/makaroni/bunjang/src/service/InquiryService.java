package shop.makaroni.bunjang.src.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.src.dao.InquiryDao;
import shop.makaroni.bunjang.src.domain.inquiry.Inquiry;
import shop.makaroni.bunjang.src.domain.inquiry.dto.InquirySaveRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class InquiryService {

	private final InquiryDao inquiryDao;

	public Long save(Long userIdx, Long storeIdx, InquirySaveRequest request) {
		Inquiry inquiry = inquiryDao.save(createInquiry(userIdx, storeIdx, request));
		return inquiry.getIdx();
	}

	private Inquiry createInquiry(Long userIdx, Long storeIdx, InquirySaveRequest request) {
		request.checkParentIdx();
		return Inquiry.builder()
				.userIdx(userIdx)
				.storeIdx(storeIdx)
				.parentIdx(request.getParentIdx())
				.post(request.getPost())
				.build();
	}
}
