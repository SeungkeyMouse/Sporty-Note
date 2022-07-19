package com.sportynote.server.service;

import com.sportynote.server.repository.NoteNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NoteService {
    private final NoteNodeRepository noteNodeRepository;

    //if(노드를 저장할때) : 이미 노트가 있으면 거기에 저장.
    //else : 노트 찾아서 거기에 연결

}
