package com.jiuyv.hhc.console.basic.action;

/**
 * 批量上传数据的基本VO,用于有具体数据状态提示的批量上传
 * @author guanzh
 *
 */
public class BatchUploadBaseVo{
	
	private String dataStatue;//数据状态
	private boolean valid;//是否有效

	public String getDataStatue() {
		return dataStatue;
	}
	public void setDataStatue(String dataStatue) {
		this.dataStatue = dataStatue;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	/**
	 * marked for status
	 * @param dataStatue
	 * @param valid
	 */
	public void dataMarked(String dataStatue,boolean valid){
		this.dataStatue = dataStatue;
		this.valid = valid;
	}

}
