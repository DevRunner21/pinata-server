package com.nexters.pinataserver.common.image;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nexters.pinataserver.common.dto.response.CommonApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ImageUploadController {

	private final ImageUploadService imageUploadService;

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/api/v1/images")
	public CommonApiResponse createBoards(@RequestParam(value = "files", required = false) List<MultipartFile> multipartFile) throws
		IOException {
		List<String> uploadedUrls = imageUploadService.upload(multipartFile);

		return CommonApiResponse.ok(ImageUploadResponse.of(uploadedUrls));
	}

}
