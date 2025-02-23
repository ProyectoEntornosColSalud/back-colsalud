package com.calderon.denv.pep.repository.app;

import com.calderon.denv.pep.model.app.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {}
