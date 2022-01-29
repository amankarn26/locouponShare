package com.locoupon.common.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.locoupon.common.enums.EnumBoolean;
import com.locoupon.common.util.UtilFuncs;


@XmlRootElement(name = "document")
@Entity()
@Table(name = "document")
//@Cacheable("document")
public class Document implements Serializable {

	private static final long serialVersionUID = -770730063917826084L;

	private Long id;
	private String docpath;
	private String docname;
	private String docdesc;
	private String doctype;
	private Timestamp updatedon;
	private EnumBoolean.type isprimary;

	public Document() {
		// TODO Auto-generated constructor stub
	}

	public Document(String relativepath, String docname) {
		this.setDocname(docname);
		this.setDocpath(relativepath);
		this.setDocdesc("PDF Invoice for Sale");
		this.setDoctype("INVOICE");
		this.setUpdatedon(UtilFuncs.getCurrentTime());
		this.setIsprimary(EnumBoolean.type.NO);
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@XmlElement(name = "docdesc")
	public String getDocdesc() {
		return docdesc;
	}

	public void setDocdesc(String docdesc) {
		this.docdesc = docdesc;
	}

	@XmlElement(name = "docname")
	public String getDocname() {
		return docname;
	}

	public void setDocname(String docname) {
		this.docname = docname;
	}

	@XmlElement(name = "doctype")
	public String getDoctype() {
		return doctype;
	}

	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}

	@XmlElement(name = "docpath")
//	@Column(unique = true)
	public String getDocpath() {
		return docpath;
	}

	public void setDocpath(String docpath) {
		this.docpath = docpath;
	}

	@Override
	public String toString() {
		return "Document [ docpath=" + docpath + ", docname=" + docname + ", docdesc=" + docdesc + ", doctype="
				+ doctype + "]";
	}

	@Enumerated(EnumType.STRING)
	@XmlElement(name = "isprimary")
	public EnumBoolean.type getIsprimary() {
		return isprimary;
	}

	public void setIsprimary(EnumBoolean.type isprimary) {
		this.isprimary = isprimary;
	}

	@XmlElement(name = "updatedon")
	public Timestamp getUpdatedon() {
		return updatedon;
	}

	public void setUpdatedon(Timestamp updatedon) {
		this.updatedon = updatedon;
	}

}
