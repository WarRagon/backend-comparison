package com.example.springboot.sample;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SampleRepository extends JpaRepository<SampleDomain, Long>{
    @EntityGraph(attributePaths = "imageList")
    @Query("select p from SampleDomain p where p.no= :no")
    Optional<SampleDomain> selectOne(@Param("no") Long no);

    @Query("select max(s.no) from SampleDomain s")
    Long findMaxNo();

    @Modifying
    @Query("update SampleDomain p set p.status = :flag where p.no = :no")
    void updateToDelete(@Param("no") Long no, @Param("flag") String flag);

    @Query("select p, pi from SampleDomain p left join p.imageList pi where pi.size = 0 and p.status = 'A' ")
    Page<Object[]> selectList(Pageable pageable);
}
