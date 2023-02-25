import SlideRequest from "@/entities/SlideRequest";
import styles from "./carouselCard.module.scss";

interface Props {
  slideRequest: SlideRequest;
  setSlideRequest: (slideRequest: SlideRequest) => void;
}

export const StyleCard = ({ slideRequest, setSlideRequest }: Props) => {
  const secondaryColor = slideRequest.secondaryColor ?? "";
  const tertiaryColor = slideRequest.tertiaryColor ?? "";

  return (
    <div className={styles.cardContainer}>
      <div className={styles.formField}>
        <label htmlFor="titleInput" className={styles.inputLabel}>
          Font family name
        </label>
        <input
          value={slideRequest.fontFamily}
          type="text"
          id="titleInput"
          className={`${styles.input} ${styles.marginBottom}`}
          placeholder="Poppins"
          onChange={(e) => {
            setSlideRequest({ ...slideRequest, fontFamily: e.target.value });
          }}
        />
      </div>

      <label className={styles.inputLabel}>Color Palette</label>

      <div className={styles.colorSelector}>
        <div className={styles.colorInputContainer}>
          <span className={styles.subTitle}>Primary color</span>
          <input
            value={slideRequest.primaryColor}
            className={styles.colorInput}
            type="color"
            id="primaryColor"
            onChange={(e) => {
              setSlideRequest({
                ...slideRequest,
                primaryColor: e.target.value,
              });
            }}
          />
        </div>
        <div className={styles.colorInputContainer}>
          <span className={styles.subTitle}>Secondary Color</span>
          <input
            value={secondaryColor}
            className={styles.colorInput}
            type="color"
            id="secondaryColor"
            onChange={(e) => {
              setSlideRequest({
                ...slideRequest,
                secondaryColor: e.target.value,
              });
            }}
          />
        </div>
        <div className={styles.colorInputContainer}>
          <span className={styles.subTitle}>Tertiary Color</span>
          <input
            value={tertiaryColor}
            className={styles.colorInput}
            type="color"
            id="tertiaryColor"
            onChange={(e) => {
              setSlideRequest({
                ...slideRequest,
                tertiaryColor: e.target.value,
              });
            }}
          />
        </div>
      </div>
    </div>
  );
};
