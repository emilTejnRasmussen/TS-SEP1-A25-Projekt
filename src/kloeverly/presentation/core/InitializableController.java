package kloeverly.presentation.core;

import kloeverly.persistence.DataManager;

public interface InitializableController
{
    void init(DataManager dataManager);
}
