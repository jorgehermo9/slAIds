import Presentation from "@/entities/Presentation";
import PresentationDto, {
  PresentationDtoSchema,
  toPresentation,
} from "@/entities/PresentationDto";
import PresentationFile from "@/entities/PresentationFile";
import PresentationRequest, {
  toPresentationRequestDto,
} from "@/entities/PresentationRequest";
import PresentationRequestDto from "@/entities/PresentationRequestDto";

export default abstract class PresentationSerivce {
  private static endpoint = "/api";

  static async generatePresentation(
    presentationRequest: PresentationRequest
  ): Promise<number> {
    const presentationRequestDto: PresentationRequestDto =
      toPresentationRequestDto(presentationRequest);
    return fetch(`${this.endpoint}/presentations/generate`, {
      method: "POST",
      body: JSON.stringify(presentationRequestDto),
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${sessionStorage.getItem("serviceToken")}`,
      },
    })
      .then((res) => {
        if (!res.ok) throw new Error(res.statusText);
        return res.text();
      })
      .then((res) => parseInt(res));
  }

  static async isAvailable(id: Presentation["id"]): Promise<boolean> {
    return fetch(`${this.endpoint}/presentations/${id}/is-available`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem("serviceToken")}`,
      },
    })
      .then((res) => {
        if (!res.ok) throw new Error(res.statusText);
        return res.text();
      })
      .then((res) => res === "true");
  }

  static async getPresentation(id: Presentation["id"]): Promise<Presentation> {
    return fetch(`${this.endpoint}/presentations/${id}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem("serviceToken")}`,
      },
    })
      .then((res) => res.json())
      .then((res) => {
        const presentationDto = PresentationDtoSchema.parse(res);
        return toPresentation(res);
      });
  }

  static async getAllPresentations(): Promise<Presentation[]> {
    return fetch(`${this.endpoint}/presentations`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem("serviceToken")}`,
      },
    })
      .then((res) => res.json())
      .then((res) => {
        const presentationDtos: PresentationDto[] = res.map(
          (presentationDto: any) => PresentationDtoSchema.parse(presentationDto)
        );
        return presentationDtos.map((presentationDto) =>
          toPresentation(presentationDto)
        );
      });
  }

  static async getPresentationFile(
    id: Presentation["id"]
  ): Promise<PresentationFile> {
    return fetch(`${this.endpoint}/presentations/${id}/pdf`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem("serviceToken")}`,
      },
    })
      .then((res) => res.blob())
      .then((res) => res);
  }
}
