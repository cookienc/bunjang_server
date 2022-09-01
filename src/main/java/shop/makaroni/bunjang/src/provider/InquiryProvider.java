package shop.makaroni.bunjang.src.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.src.dao.InquiryDao;
import shop.makaroni.bunjang.src.dao.ItemDao;
import shop.makaroni.bunjang.src.dao.UserDao;
import shop.makaroni.bunjang.src.domain.inquiry.model.GetInquiryRes;
import shop.makaroni.bunjang.src.domain.inquiry.view.InquirySimpleResponse;
import shop.makaroni.bunjang.src.domain.inquiry.view.InquirySimpleView;

import java.util.List;
import java.util.stream.Collectors;

import static shop.makaroni.bunjang.config.BaseResponseStatus.INQUIRY_INVALID_TARGET;



@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InquiryProvider {

	private final InquiryDao inquiryDao;
	private final ItemDao itemDao;
	private final UserDao userDao;

	public List<InquirySimpleView> getInquiryInfo(Long storeIdx) {
		List<InquirySimpleResponse> inquiries = inquiryDao.findAllByStoreId(storeIdx);
		return inquiries.stream()
				.map(InquirySimpleView::of)
				.collect(Collectors.toList());
	}

	public List<GetInquiryRes> getInquiry(Long targetIdx, char type) throws BaseException {
		if ((type == 'I' && itemDao.checkItemIdx(targetIdx) == 0) ||
				(type == 'S' && userDao.checkUserIdx(targetIdx) == 0)) {
			throw new BaseException(INQUIRY_INVALID_TARGET);
		}
		return inquiryDao.getInquiry(targetIdx, type);
	}


}
