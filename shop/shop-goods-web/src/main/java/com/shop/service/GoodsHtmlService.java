package com.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2021/3/12 21:55
 */
@Service
public class GoodsHtmlService {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private GoodsService goodsService;


    public void createHtml (Long spuId){
        //初始化运行上下文
        Context context = new Context();
        context.setVariables(goodsService.loadData(spuId));

        PrintWriter printWriter = null;
        try {
            //把静态文件生成到服务器本地
            File file = new File("D:\\develop\\nginx\\nginx-1.4.7\\nginx-1.4.7\\shop\\item\\" + spuId + ".html");
             printWriter = new PrintWriter(file);
            templateEngine.process("item",context,printWriter);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }finally {
            if (printWriter!=null){
                printWriter.close();
            }
        }

    }

    public void deleteHtml(Long spuId) {
        File file = new File("D:\\develop\\nginx\\nginx-1.4.7\\nginx-1.4.7\\shop\\item\\" + spuId + ".html");
        file.deleteOnExit();
    }
}
