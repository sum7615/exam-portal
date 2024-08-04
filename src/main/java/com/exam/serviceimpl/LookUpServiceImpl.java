package com.exam.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.dto.LookUpDataDto;
import com.exam.entity.AppProperties;
import com.exam.exception.NoDataFoundException;
import com.exam.repository.AppPropertiesRepo;
import com.exam.repository.SpXamException;
import com.exam.service.LookUpService;
@Service
public class LookUpServiceImpl implements LookUpService{

	@Autowired
	AppPropertiesRepo appPropertiesRepo;
	
	@Override
	public List<LookUpDataDto> getPageProp(String page) {
		List<AppProperties> allPropOfPage =null;
		try {
			allPropOfPage = appPropertiesRepo.findByPage(page);
		}catch (Exception e) {
			throw new SpXamException("Error while fetching data");
		}
		
		if(allPropOfPage!=null && allPropOfPage.size()>0) {
			List<LookUpDataDto> resultList = new ArrayList<>();
			allPropOfPage.forEach(e->{
				LookUpDataDto tempRes = new LookUpDataDto();
				BeanUtils.copyProperties(e, tempRes);
				resultList.add(tempRes);
			});
			return resultList;
			
			
		}else {
			throw new NoDataFoundException("No Data found");
		}
	}

}
