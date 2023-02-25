import Presentation from "@/entities/Presentation";
import PresentationService from "@/services/PresentationService";
import { useContext, useEffect, useState } from "react";
import { NotificationContext } from "../NotificationManager/NotificationManager";
import styles from "./presentationList.module.scss";
import VisibilityIcon from "@mui/icons-material/Visibility";
import { AnimatePresence } from "framer-motion";
import { Preview } from "../Preview/Preview";
import PresentationFile from "@/entities/PresentationFile";
import DownloadIcon from "@mui/icons-material/Download";

export const PresentationList = () => {
  const { createErrorNotification } = useContext(NotificationContext)!;
  const [presentations, setPresentation] = useState<Presentation[]>([]);
  const [isPreviewOpen, setIsPreviewOpen] = useState(false);
  const [presentationFile, setPresentationFile] =
    useState<PresentationFile | null>();

  const handleClickPreview = (presentation: Presentation) => {
    PresentationService.getPresentationFile(presentation.id)
      .then((res) => {
        setPresentationFile(res);
        setIsPreviewOpen(true);
      })
      .catch(() =>
        createErrorNotification("Error while fetching presentation file", 5000)
      );
  };

  useEffect(() => {
    PresentationService.getAllPresentations()
      .then((res) => {
        setPresentation(res);
      })
      .catch(() =>
        createErrorNotification("Error while fetching presentations", 5000)
      );
  }, [createErrorNotification]);

  return (
    <>
      <AnimatePresence>
        {isPreviewOpen && presentationFile && (
          <Preview
            presentationFile={presentationFile}
            onClose={() => setIsPreviewOpen(false)}
          />
        )}
      </AnimatePresence>
      <h1 className={styles.title}>Presentations</h1>
      <div className={styles.listContainer}>
        {presentations.map((presentation, i) => (
          <div key={i} className={styles.listItemContainer}>
            <h2 className={styles.itemTitle}>{presentation.title}</h2>
            <div className={styles.buttonsContainer}>
              <VisibilityIcon
                onClick={() => handleClickPreview(presentation)}
                className={styles.itemIcon}
              />
              <div className={styles.downloadsWrapper}>
                <span>Download</span>
                <div className={styles.downloadsButtonContainer}>
                  <a
                    className={`${styles.pdfButton} ${styles.downloadsButton}`}
                    href={`/api/presentations/${presentation.id}/pdf`}
                    download="bill.pdf"
                    target="_blank"
                    rel="noreferrer"
                  >
                    <span>pdf</span>
                    <DownloadIcon />
                  </a>
                  <a
                    className={`${styles.pptButton} ${styles.downloadsButton}`}
                    href={`/api/bills/${presentation.id}/pptx`}
                    download="bill.pdf"
                    target="_blank"
                    rel="noreferrer"
                  >
                    <span>ppt</span>
                    <DownloadIcon />
                  </a>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </>
  );
};
