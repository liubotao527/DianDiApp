package com.example.xdcao.diandiapp.DdService.liubotao.PicsSelect.multiphotopicker.model;

import java.io.Serializable;

/**
 * 图片对象
 *
 */
public class ImageItem implements Serializable
{
	private static final long serialVersionUID = -7188270558443739436L;
	public String imageId;
	public int listPosition; // add assur 
	public String thumbnailPath;
	public String sourcePath;
	public boolean isSelected = false;
}
