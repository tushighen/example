package com.golomt.example.service.utilities;

import com.golomt.example.constant.Constants;
import com.golomt.example.dto.IGeneralDTO;
import com.golomt.example.dto.ResponseDTO;

import java.util.List;

/**
 * Response Service @author Tushig
 */

public class ResponseService implements Constants {

    /**
     * DTO
     **/

    private IGeneralDTO dto;

    private List<IGeneralDTO> dtos;

    private ResponseDTO responseDTO;

    /**
     * Constructor
     **/

    public ResponseService(int code, String message, IGeneralDTO contentDTO) {
        this.getResponseDTO().getHeader().setCode(code);
        this.getResponseDTO().getHeader().setStatus(code == 200 ? Status.SUCCESS : Status.FAILED);
        this.getResponseDTO().getHeader().setMessage(message);
        dto = contentDTO;
    }

    /**
     * Getter.Setter
     **/

    public ResponseDTO getResponse() {
        this.getResponseDTO().getBody().setResponse(dto);
        return this.getResponseDTO();
    }

    public ResponseDTO getError() {
        this.getResponseDTO().getBody().setError(dto);
        return this.getResponseDTO();
    }

    public ResponseDTO getSuccess() {
        this.getResponseDTO().getBody().setSuccess(dto);
        return this.getResponseDTO();
    }

    private ResponseDTO getResponseDTO() {
        return responseDTO != null ? this.responseDTO : (this.responseDTO = new ResponseDTO());
    }

    private void setResponseDTO(ResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }
}
