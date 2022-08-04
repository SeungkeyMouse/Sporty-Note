package com.sportynote.server.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sportynote.server.domain.Gym;
import com.sportynote.server.domain.QRecord;
import com.sportynote.server.domain.Record;
import com.sportynote.server.domain.Routine;
import com.sportynote.server.repository.query.RecordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecordRepository {
    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    QRecord m = new QRecord("m");

    public void save(Record record) {
        em.persist(record);
    }
    public void deleteRecord(Long idx){
        em.remove(em.find(Record.class, idx));
    }
    public Record findByIdentityId(RecordDto recordDto){
        return em.createQuery("select m from Record m where m.userBasic.userId=:userId and m.machine.idx=:idx and m.sett=:sett",Record.class)
                .setParameter("userId",recordDto.getUserId())
                .setParameter("idx",recordDto.getMachineIdx())
                .setParameter("sett",recordDto.getSett()).getSingleResult();
    }

    public List<LocalDate> findByCalendar(String userid){
        return jpaQueryFactory
                .select(m.createdDay).distinct()
                .from(m).fetch();
    }

    public List<Record> findByRecordDay(LocalDate localDate,String userId){
        return jpaQueryFactory.select(m).from(m).where(m.createdDay.eq(localDate).and(m.userBasic.userId.eq(userId))).fetch();
    }
}
