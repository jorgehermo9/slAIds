import PresentationRequest from "@/entities/PresentationRequest";
import styles from "./carouselCard.module.scss";

interface Props {
  presentationRequest: PresentationRequest;
  setPresentationRequest: (presentationRequest: PresentationRequest) => void;
}

export const StyleCard = ({
  presentationRequest,
  setPresentationRequest,
}: Props) => {
  const secondaryColor = presentationRequest.secondaryColor ?? "";
  const tertiaryColor = presentationRequest.tertiaryColor ?? "";

  return (
    <div className={styles.cardContainer}>
      <div className={styles.formField}>
        <label htmlFor="titleInput" className={styles.inputLabel}>
          Font family name
        </label>
        <input
          value={presentationRequest.fontFamily}
          type="text"
          id="titleInput"
          className={`${styles.input} ${styles.marginBottom}`}
          placeholder="Poppins"
          onChange={(e) => {
            setPresentationRequest({
              ...presentationRequest,
              fontFamily: e.target.value,
            });
          }}
        />
      </div>

      <label className={styles.inputLabel}>Color Palette</label>

      <div className={styles.colorSelector}>
        <div className={styles.colorInputContainer}>
          <span className={styles.subTitle}>Primary color</span>
          <input
            value={presentationRequest.primaryColor}
            className={styles.colorInput}
            type="color"
            id="primaryColor"
            onChange={(e) => {
              setPresentationRequest({
                ...presentationRequest,
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
              setPresentationRequest({
                ...presentationRequest,
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
              setPresentationRequest({
                ...presentationRequest,
                tertiaryColor: e.target.value,
              });
            }}
          />
        </div>
      </div>
    </div>
  );
};
