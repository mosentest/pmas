package org.mo.znyunxt.entity;

/**
 * 进出类型
 * 
 * @author Administrator
 *
 */
public class TbIo {
	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 进出代码
	 */
	private String io;
	/**
	 * 门代码
	 */
	private String gate;
	/**
	 * 进或出 出0，进1
	 */
	private String ioType;
	/**
	 * 进出短名
	 */
	private String ioSortName;
	/**
	 * 大门类型 0校门，1宿舍
	 */
	private String gateType;
	/**
	 * 0不在校，1在校
	 */
	private String inschool;
	/**
	 * 进出名称,中文名称
	 */
	private String ioname;
}
