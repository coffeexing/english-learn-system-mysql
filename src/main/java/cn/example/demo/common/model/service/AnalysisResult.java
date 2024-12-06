package cn.example.demo.common.model.service;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>
 * Excel 文件导入分析结果
 * </p>
 *
 * @author Lizuxian
 * @create 2020/9/7 15:46
 */
@Data
@AllArgsConstructor
public class AnalysisResult<T> extends ServiceResult {
    private int updateRows;

    public AnalysisResult(int status, String message, int updateRows) {
        super(status, message);
        this.updateRows = updateRows;
    }

    public static AnalysisResult isSuccess(int updateRows) {
        return new AnalysisResult(Status.SUCCESS.code, Status.SUCCESS.name, updateRows);
    }

    public static AnalysisResult isEmpty() {
        return new AnalysisResult(Status.EMPTY.code, Status.EMPTY.name, 0);
    }

    public static AnalysisResult isInvalidFormat() {
        return new AnalysisResult(Status.INVALID_FORMAT.code, Status.INVALID_FORMAT.name, 0);
    }

    public static AnalysisResult isError() {
        return new AnalysisResult(Status.ERROR.code, Status.ERROR.name, 0);
    }

    /**
     * <p>
     * 分析结果状态
     * </P>
     */
    public enum Status {
        SUCCESS(200, "文件上传分析成功！"),
        EMPTY(404, "没有数据可以分析！"),
        INVALID_FORMAT(400, "Excel文件中的字段名称、格式等不符合规范，请按照模板文件导入！"),
        ERROR(500, "数据分析期间发生未知错误！");

        private final int code;
        private final String name;

        Status(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }
}
