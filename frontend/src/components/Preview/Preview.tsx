import PresentationFile from "@/entities/PresentationFile";
import styles from "./preview.module.scss";

interface Props {
  presentationFile: PresentationFile;
  onClose: () => void;
}

export const Preview = ({ presentationFile }: Props) => {
  return (
    <div className={styles.overlay}>
      <div className={styles.modal}>
        <h1>Preview</h1>
        <p>{presentationFile.name}</p>
      </div>
    </div>
  );
};
