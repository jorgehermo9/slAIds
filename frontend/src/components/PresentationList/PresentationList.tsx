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
import PresentationListDto from "@/entities/PresentationListDto";
import { useRouter } from "next/router";

export const PresentationList = () => {
  const { createErrorNotification } = useContext(NotificationContext)!;
  const [presentations, setPresentation] = useState<PresentationListDto[]>([]);
  const [isPreviewOpen, setIsPreviewOpen] = useState(false);
  const [presentationFile, setPresentationFile] =
    useState<PresentationFile | null>();
  const router = useRouter();

  const handleClickPreview = (presentation: PresentationListDto) => {
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

  const [token, setToken] = useState<string | null>(null);
  useEffect(() => {
    setToken(sessionStorage.getItem("serviceToken"));
  }, []);
  

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
        {presentations.length === 0 ? (
          <div className={styles.noPresentationsContainer}>
            <h2 className={styles.noPresentationsText}>
              You have no presentations yet
            </h2>
          </div>
        ) : (
          presentations.map((presentation, i) => (
            <div key={i} className={styles.listItemContainer}>
              <h2 className={styles.itemTitle}>{presentation.title}</h2>
              <div className={styles.buttonsContainer}>
                <VisibilityIcon
                  onClick={() => handleClickPreview(presentation)}
                  className={styles.itemIcon}
                />
                <a
                    href={`/api/presentations/${presentation.id}/download/pdf?token=${token}`}
                    download={`${presentation.title}.pdf`}
                  className={`${styles.pdfButton} ${styles.downloadsButton}`}
                >
                  <span>pdf</span>
                  <DownloadIcon />
                </a>
                <a 
                 href={`/api/presentations/${presentation.id}/download/pptx?token=${token}`}
                 download={`${presentation.title}.pdf`}
                 className={`${styles.pptButton} ${styles.downloadsButton}`}>
                  <span>ppt</span>
                  <DownloadIcon />
                </a>
              </div>
            </div>
          ))
        )}
      </div>
    </>
  );
};
