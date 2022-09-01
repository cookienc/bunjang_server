package shop.makaroni.bunjang.src.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.src.dao.InquiryDao;
import shop.makaroni.bunjang.src.dao.ItemDao;
import shop.makaroni.bunjang.src.dao.UserDao;
import shop.makaroni.bunjang.src.domain.inquiry.Inquiry;
import shop.makaroni.bunjang.src.domain.inquiry.dto.InquirySaveRequest;
import shop.makaroni.bunjang.src.domain.inquiry.model.PostInqueryReq;

import java.util.HashMap;

import static shop.makaroni.bunjang.config.BaseResponseStatus.INQUIRY_INVALID_PARENTIDX;
import static shop.makaroni.bunjang.config.BaseResponseStatus.INQUIRY_INVALID_TARGET;
import static shop.makaroni.bunjang.config.BaseResponseStatus.INVALID_USER_JWT;
import static shop.makaroni.bunjang.config.BaseResponseStatus.REQUEST_ERROR;

@Service
@Transactional
@RequiredArgsConstructor
public class InquiryService {

	private final InquiryDao inquiryDao;
	private final ItemDao itemDao;
	private final UserDao userDao;
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
	public HashMap<String, String> PostInquiry(Long userIdx, PostInqueryReq postInqueryReq) throws BaseException {
		if(itemDao.checkItemIdx(postInqueryReq.getTargetIdx()) == 0){
			throw new BaseException(INQUIRY_INVALID_TARGET);
		}
		if(postInqueryReq.getParentIdx() != 0 && inquiryDao.checkInquiry(postInqueryReq.getParentIdx()) == 0){
			throw new BaseException(INQUIRY_INVALID_PARENTIDX);
		}
		return inquiryDao.PostInquiry(userIdx, postInqueryReq);
	}

	public HashMap<String, String> DeleteInquiry(Long userIdx, Long inquiryIdx) throws BaseException {
		if(inquiryDao.checkInquiry(inquiryIdx) == 0){
			throw new BaseException(REQUEST_ERROR);
		}
		if(!userIdx.equals(inquiryDao.getUserIdx(inquiryIdx))){
			throw new BaseException(INVALID_USER_JWT);
		}
		return inquiryDao.DeleteInquiry(inquiryIdx);
	}
}
