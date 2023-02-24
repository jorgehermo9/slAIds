import SlideRequest from "@/entities/SlideRequest";
import styles from "./styleCard.module.scss";

interface Props {
  slideRequest: SlideRequest;
  setSlideRequest: (slideRequest: SlideRequest) => void;
}

export const OptionsCard = ({ slideRequest, setSlideRequest }: Props) => {
  const maxSlides = slideRequest.maxSlides ?? 0;

  return (
    <div className={styles.resumeCard}>
      <div className={styles.formField}>
        <label htmlFor="maxSlides" className={styles.title}>
          Number of maximum slides
        </label>
        <input
          value={maxSlides}
          type="number"
          min="1"
          id="maxSlides"
          className={styles.input}
          onChange={(e) => {
            setSlideRequest({
              ...slideRequest,
              maxSlides: parseInt(e.target.value),
            });
          }}
        />
      </div>
    </div>
  );
};
