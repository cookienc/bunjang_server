package shop.makaroni.bunjang.src.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.src.dao.InquiryDao;
import shop.makaroni.bunjang.src.domain.inquiry.view.InquirySimpleResponse;
import shop.makaroni.bunjang.src.domain.inquiry.view.InquirySimpleView;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InquiryProvider {

	private final InquiryDao inquiryDao;

	public List<InquirySimpleView> getInquiryInfo(Long storeIdx) {
		List<InquirySimpleResponse> inquiries = inquiryDao.findAllByStoreId(storeIdx);
		return inquiries.stream()
				.map(InquirySimpleView::of)
				.collect(Collectors.toList());
	}
}
