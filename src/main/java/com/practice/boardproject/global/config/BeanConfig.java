package com.practice.boardproject.global.config;

import com.practice.boardproject.post.domain.Post;
import com.practice.boardproject.post.dto.PostDetailDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);

        // 작성자 매핑 시 이름으로 매핑해준다.
        modelMapper.createTypeMap(Post.class, PostDetailDTO.class)
                .addMapping(
                        src -> src.getAuthor().getUsername(),
                        (postDetailDTO, o) -> postDetailDTO.setAuthor((String) o)
                );
        return modelMapper;
    }
}
