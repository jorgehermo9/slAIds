import SlideRequest from "@/entities/SlideRequest";
import styles from "./styleCard.module.scss";

interface Props {
  slideRequest: SlideRequest;
  setSlideRequest: (slideRequest: SlideRequest) => void;
}

export const StyleCard = ({ slideRequest, setSlideRequest }: Props) => {
  return (
    <div className={styles.resumeCard}>
      <label htmlFor="titleInput" className={styles.title}>
        Font family name
      </label>
      <input
        value={slideRequest.fontFamily}
        type="text"
        id="titleInput"
        className={`${styles.input} ${styles.marginBottom}`}
        placeholder="FIC slides for 2023"
        onChange={(e) => {
          setSlideRequest({ ...slideRequest, fontFamily: e.target.value });
        }}
      />
      <label htmlFor="primaryColor" className={styles.title}>
        Color Palette
      </label>

      <div className="colorSelector">
        <label htmlFor="primaryColor" className={styles.title}>
          Primary color
        </label>
        <input type="color" id="primaryColor" />
        <label htmlFor="secondaryColor" className={styles.title}>
          Secondary Color
        </label>
        <input type="color" id="secondaryColor" />
        <label htmlFor="tertiaryColor" className={styles.title}>
          Secondary Color
        </label>
        <input type="color" id="tertiaryColor" />
      </div>
    </div>
  );
};
