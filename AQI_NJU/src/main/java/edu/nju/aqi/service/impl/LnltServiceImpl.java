package edu.nju.aqi.service.impl;

import edu.nju.aqi.dao.LnltDao;
import edu.nju.aqi.model.Lnlt;
import edu.nju.aqi.service.LnltService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lulei on 16/3/17.
 */
@Service
public class LnltServiceImpl implements LnltService{
    @Autowired
    private LnltDao lnltDao;
    @Override
    public List<Lnlt> getAllLnlt() {
        return lnltDao.getAllLnlt();
    }
}
