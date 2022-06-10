package zust.competition.sys.dto;

import lombok.Data;

@Data
//为界面表格返回Json数据
public class TableVo {
    private Integer code;
    private String msg;
    private Object data;

    public TableVo(){
    }
    public TableVo(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }
}
