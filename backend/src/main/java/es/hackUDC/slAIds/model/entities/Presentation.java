package es.hackUDC.slAIds.model.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Presentation {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(columnDefinition = "TEXT")
  private String title;

  @Column(columnDefinition = "TEXT")
  private String descriptionPrompt;

  @OneToOne(cascade = CascadeType.ALL)
  private Index index;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "presentation_id")
  private List<Slide> slides;

  private byte[] frontImg;

  private Boolean isAvailable = false;

  private byte[] pptx;

  private Boolean error;

  @Column(columnDefinition = "TEXT")
  private String errorMessage;

  private byte[] pdf;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "userId")
  private ModelUser modelUser;

  public Presentation() {
  }

  public Presentation(String title, String descriptionPrompt, Index index, List<Slide> slides, ModelUser modelUser) {
    this.title = title;
    this.descriptionPrompt = descriptionPrompt;
    this.index = index;
    this.slides = slides;
    this.modelUser = modelUser;
  }

  public byte[] getPdf() {
    return pdf;
  }

  public void setPdf(byte[] pdf) {
    this.pdf = pdf;
  }

  public Boolean getIsAvailable() {
    return isAvailable;
  }

  public void setIsAvailable(Boolean isAvailable) {
    this.isAvailable = isAvailable;
  }

  public Boolean getError() {
    return error;
  }

  public void setError(Boolean error) {
    this.error = error;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescriptionPrompt() {
    return descriptionPrompt;
  }

  public void setDescriptionPrompt(String descriptionPrompt) {
    this.descriptionPrompt = descriptionPrompt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Index getIndex() {
    return index;
  }

  public void setIndex(Index index) {
    this.index = index;
  }

  public List<Slide> getSlides() {
    return slides;
  }

  public void setSlides(List<Slide> slides) {
    this.slides = slides;
  }

  public byte[] getPptx() {
    return pptx;
  }

  public void setPptx(byte[] pptx) {
    this.pptx = pptx;
  }

  public byte[] getFrontImg() {
    return frontImg;
  }

  public void setFrontImg(byte[] frontImg) {
    this.frontImg = frontImg;
  }

  public ModelUser getModelUser() {
    return modelUser;
  }

  public void setModelUser(ModelUser modelUser) {
    this.modelUser = modelUser;
  }

}
