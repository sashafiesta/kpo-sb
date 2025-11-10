package com.sashafiesta.finances.repository.impl;

import com.sashafiesta.finances.domain.Operation;
import com.sashafiesta.finances.repository.OperationRepository;

public class SimpleOperationRepository extends SimpleRepository<Operation> implements OperationRepository {
    public SimpleOperationRepository() {
        super(Operation::getId);
    }
}

