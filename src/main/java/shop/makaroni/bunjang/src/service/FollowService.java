package shop.makaroni.bunjang.src.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.src.dao.FollowDao;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {

	private final FollowDao followDao;


}
