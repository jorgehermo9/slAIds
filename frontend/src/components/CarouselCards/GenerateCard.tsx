import SlideRequest from "@/entities/SlideRequest";
import styles from "./generateCard.module.scss";
import SendRoundedIcon from "@mui/icons-material/SendRounded";

interface Props {
  slideRequest: SlideRequest;
  setSlideRequest: (slideRequest: SlideRequest) => void;
}

export const GenerateCard = ({ slideRequest, setSlideRequest }: Props) => {
  return (
    <div className={styles.resumeCard}>
      <button className={styles.generateButton}>
        Generate
        <SendRoundedIcon className={styles.icon} />
      </button>
    </div>
  );
};
