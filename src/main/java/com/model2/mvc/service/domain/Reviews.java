package com.model2.mvc.service.domain;

import java.sql.Date;

public class Reviews {
	
	private int reviewNo;	//�����ȣ
	private User userId;	//�������̵�
	private Purchase tranNo; // ���Ź�ȣ
	private Product prodName; //��ǰ��
	private Product image; //��ǰ�̹���
	private String text;  //���䳻��
	private Date regDate;        //���䳯¥
	
	public Reviews(){
	}

	public int getReviewNo() {
		return reviewNo;
	}

	public void setReviewNo(int reviewNo) {
		this.reviewNo = reviewNo;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	public Product getImage() {
		return image;
	}

	public void setImage(Product image) {
		this.image = image;
	}

	public Product getProdName() {
		return prodName;
	}

	public void setProdName(Product prodName) {
		this.prodName = prodName;
	}

	public Purchase getTranNo() {
		return tranNo;
	}

	public void setTranNo(Purchase tranNo) {
		this.tranNo = tranNo;
	}
	@Override
	public String toString() {
		return "Review [reviewNo=" + reviewNo + ", tranNo=" + tranNo
				+ ", userId=" + userId + ", prodName=" + prodName 
				+ ", image="+ image + ", text=" + text
				+ ", regDate=" + regDate + "]";
	}
}